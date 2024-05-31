import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ColumnType
import org.jetbrains.exposed.sql.Table
import org.postgresql.util.PGobject
import java.sql.PreparedStatement
import java.sql.ResultSet

class IntArrayColumnType : ColumnType() {
    override fun sqlType(): String = "INTEGER[]"

    fun setParameter(stmt: PreparedStatement, index: Int, value: Any?) {
        if (value == null) {
            stmt.setNull(index, java.sql.Types.ARRAY)
        } else {
            val array = stmt.connection.createArrayOf("INTEGER", (value as List<*>).toTypedArray())
            stmt.setArray(index, array)
        }
    }

    override fun valueFromDB(value: Any): Any {
        return when (value) {
            is PGobject -> value.value?.removeSurrounding("{", "}")?.split(",")?.map { it.toInt() } ?: emptyList<Int>()
            is java.sql.Array -> {
                val array = value.array as Array<*>
                array.filterIsInstance<Int>()
            }
            else -> value
        }
    }

    override fun notNullValueToDB(value: Any): Any {
        return when (value) {
            is List<*> -> value.joinToString(",", "{", "}")
            else -> value
        }
    }

     fun nullableValueToDB(value: Any?): Any? {
        return when (value) {
            null -> null
            is List<*> -> value.joinToString(",", "{", "}")
            else -> value
        }
    }

    override fun readObject(rs: ResultSet, index: Int): Any? {
        return rs.getArray(index)?.array as? Array<Int>
    }
}

fun Table.integerArray(name: String): Column<List<Int>> = registerColumn(name, IntArrayColumnType())
