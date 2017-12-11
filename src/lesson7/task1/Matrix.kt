@file:Suppress("UNUSED_PARAMETER", "unused")

package lesson7.task1

/**
 * Ячейка матрицы: row = ряд, column = колонка
 */
data class Cell(val row: Int, val column: Int)

/**
 * Интерфейс, описывающий возможности матрицы. E = тип элемента матрицы
 */
interface Matrix<E> {
    /** Высота 高，row一行数字的个数（横），column列数*/
    val height: Int

    /** Ширина 宽，column一列数字的个数（竖），row行数*/
    val width: Int

    /**
     * Доступ к ячейке.访问
     * Методы могут бросить исключение, если ячейка не существует или пуста
     */
    operator fun get(row: Int, column: Int): E//

    //格子
    operator fun get(cell: Cell): E

    /**
     * Запись в ячейку.记录
     * Методы могут бросить исключение, если ячейка не существует
     */
    operator fun set(row: Int, column: Int, value: E)

    operator fun set(cell: Cell, value: E)
}

/**
 * Простая
 *
 * Метод для создания матрицы, должен вернуть РЕАЛИЗАЦИЮ Matrix<E>.
 * height = высота, width = ширина, e = чем заполнить элементы.
 * Бросить исключение IllegalArgumentException, если height или width <= 0.
 */
fun <E> createMatrix(height: Int, width: Int, e: E): Matrix<E> {
    if (height <= 0 || width <= 0) {
        throw IllegalArgumentException()
    }
    val matrix = MatrixImpl(height, width, e)
    for (row in 0 until height) {
        for (column in 0 until width) {
            matrix[row, column] = e
        }
    }
    return matrix
}


/**
 * Средняя сложность
 *
 * Реализация интерфейса "матрица"
 */
class MatrixImpl<E>(override val height: Int, override val width: Int, e: E) : Matrix<E> {
    private val cellList = mutableListOf<E>()

    init {
        for (it in 0 until height * width) {
            cellList.add(e)
        }
    }

    override fun get(row: Int, column: Int): E = cellList[row * width + column]

    override fun get(cell: Cell): E = cellList[cell.row * width + cell.column]

    override fun set(row: Int, column: Int, value: E) {
        cellList[row * width + column] = value
    }

    override fun set(cell: Cell, value: E) {
        cellList[cell.row * width + cell.column] = value
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as MatrixImpl<*>

        if (height != other.height) return false
        if (width != other.width) return false
        if (cellList != other.cellList) return false

        return true
    }

    override fun hashCode(): Int {
        var result = height
        result = 31 * result + width
        result = 31 * result + cellList.hashCode()
        return result
    }

    override fun toString(): String = "MatrixImpl(height=$height, width=$width, cellList=$cellList)"
/*       val sb = StringBuilder()
        sb.append("[")
        for (row in 0 until height) {
            sb.append("[")
            for (column in 0 until width) {
                sb.append(this[row, column])
            }
            sb.append("]")
        }
        sb.append("]")
        return "$sb"
    }*/
}



