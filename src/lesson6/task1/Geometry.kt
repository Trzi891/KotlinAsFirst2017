@file:Suppress("UNUSED_PARAMETER")

package lesson6.task1

import lesson1.task1.sqr
import lesson2.task1.segmentLength
import lesson3.task1.cos
import lesson4.task1.center

/**
 * Точка на плоскости
 */
data class Point(val x: Double, val y: Double) {
    /**
     * Пример
     *
     * Рассчитать (по известной формуле) расстояние между двумя точками
     */
    fun distance(other: Point): Double = Math.sqrt(sqr(x - other.x) + sqr(y - other.y))
}

/**
 * Треугольник, заданный тремя точками (a, b, c, см. constructor ниже).
 * Эти три точки хранятся в множестве points, их порядок не имеет значения.
 */
class Triangle private constructor(private val points: Set<Point>) {

    private val pointList = points.toList()

    val a: Point get() = pointList[0]

    val b: Point get() = pointList[1]

    val c: Point get() = pointList[2]

    constructor(a: Point, b: Point, c: Point) : this(linkedSetOf(a, b, c))

    /**
     * Пример: полупериметр
     */
    fun halfPerimeter() = (a.distance(b) + b.distance(c) + c.distance(a)) / 2.0

    /**
     * Пример: площадь
     */
    fun area(): Double {
        val p = halfPerimeter()
        return Math.sqrt(p * (p - a.distance(b)) * (p - b.distance(c)) * (p - c.distance(a)))
    }

    /**
     * Пример: треугольник содержит точку
     */
    fun contains(p: Point): Boolean {
        val abp = Triangle(a, b, p)
        val bcp = Triangle(b, c, p)
        val cap = Triangle(c, a, p)
        return abp.area() + bcp.area() + cap.area() <= area()
    }

    override fun equals(other: Any?) = other is Triangle && points == other.points

    override fun hashCode() = points.hashCode()

    override fun toString() = "Triangle(a = $a, b = $b, c = $c)"
}

/**
 * Окружность с заданным центром и радиусом
 */
data class Circle(val center: Point, val radius: Double) {
    /**
     * Простая
     *
     * Рассчитать расстояние между двумя окружностями.
     * Расстояние между непересекающимися окружностями рассчитывается как
     * расстояние между их центрами минус сумма их радиусов.
     * Расстояние между пересекающимися окружностями считать равным 0.0.
     */
    fun distance(other: Circle): Double {
        val d = center.distance(other.center) - radius - other.radius
        return if (d < 0.0) 0.0 else d
    }

    /**
     * Тривиальная
     *
     * Вернуть true, если и только если окружность содержит данную точку НА себе или ВНУТРИ себя
     */
    fun contains(p: Point): Boolean = center.distance(p) <= radius
}

/**
 * Отрезок между двумя точками
 */
data class Segment(val begin: Point, val end: Point) {
    override fun equals(other: Any?) =
            other is Segment && (begin == other.begin && end == other.end || end == other.begin && begin == other.end)

    override fun hashCode() =
            begin.hashCode() + end.hashCode()
}

/**
 * Средняя
 *
 * Дано множество точек. Вернуть отрезок, соединяющий две наиболее удалённые из них.
 * Если в множестве менее двух точек, бросить IllegalArgumentException
 */
fun Segment.length() = begin.distance(end)

fun Segment.mid() = Point((begin.x + end.x) / 2, (begin.y + end.y) / 2)

fun diameter(vararg points: Point): Segment {
    var longest = Segment(points.first(), points.last())
    if (points.size < 2) {
        throw IllegalArgumentException()
    } else {
        for (element1 in points) {
            for (element2 in points) {
                if (element1.distance(element2) > longest.length()) {
                    longest = Segment(element1, element2)
                }
            }
        }
    }
    return longest
}

/**
 * Простая
 *
 * Построить окружность по её диаметру, заданному двумя точками
 * Центр её должен находиться посередине между точками, а радиус составлять половину расстояния между ними
 */
fun circleByDiameter(diameter: Segment): Circle = Circle(diameter.mid(), diameter.length() / 2)

/**
 * Прямая, заданная точкой point и углом наклона angle (в радианах) по отношению к оси X.
 * Уравнение прямой: (y - point.y) * cos(angle) = (x - point.x) * sin(angle)
 * или: y * cos(angle) = x * sin(angle) + b, где b = point.y * cos(angle) - point.x * sin(angle).
 * Угол наклона обязан находиться в диапазоне от 0 (включительно) до PI (исключительно).
 * 0..180
 */
class Line private constructor(val b: Double, val angle: Double) {
    init {
        assert(angle >= 0 && angle < Math.PI) { "Incorrect line angle: $angle" }
    }

    constructor(point: Point, angle: Double) : this(point.y * Math.cos(angle) - point.x * Math.sin(angle), angle)

    /**
     * Средняя
     *
     * Найти точку пересечения с другой линией.
     * Для этого необходимо составить и решить систему из двух уравнений (каждое для своей прямой)
     */

    fun crossPoint(other: Line): Point {
        val x = (other.b * Math.cos(angle) - b * Math.cos(other.angle)) /
                (Math.cos(other.angle) * Math.sin(angle) - Math.cos(angle) * Math.sin(other.angle))
        val y = (other.b * Math.sin(angle) - b * Math.sin(other.angle)) /
                (Math.cos(other.angle) * Math.sin(angle) - Math.cos(angle) * Math.sin(other.angle))
        return Point(x, y)
    }

    override fun equals(other: Any?) = other is Line && angle == other.angle && b == other.b

    override fun hashCode(): Int {
        var result = b.hashCode()
        result = 31 * result + angle.hashCode()
        return result
    }

    override fun toString() = "Line(${Math.cos(angle)} * y = ${Math.sin(angle)} * x + $b)"
}

/**
 * Средняя
 *
 * Построить прямую по отрезку
 */
fun lineBySegment(s: Segment): Line = Line(s.begin, Math.atan2((s.end.y - s.begin.y), (s.end.x - s.begin.x)))

/**
 * Средняя
 *
 * Построить прямую по двум точкам
 */
fun lineByPoints(a: Point, b: Point): Line = lineBySegment(Segment(a, b))

/**
 * Сложная
 *两点间的中垂线
 * Построить серединный перпендикуляр по отрезку или по двум точкам
 */
fun bisectorByPoints(a: Point, b: Point): Line = Line(Point((a.x + b.x) / 2, (a.y + b.y) / 2),
        Math.atan2((b.y - a.y), (b.x - a.x)) + Math.PI / 2)

/**
 * Средняя
 *
 * Задан список из n окружностей на плоскости. Найти пару наименее удалённых из них.
 * Если в списке менее двух окружностей, бросить IllegalArgumentException
 */
fun findNearestCirclePair(vararg circles: Circle): Pair<Circle, Circle> {
    if (circles.size < 2) {
        throw IllegalAccessException()
    } else {
        var nearestCircle1 = circles[0]
        var nearestCircle2 = circles[1]
        var min = circles[0].center.distance(circles[1].center)
        -circles[0].radius - circles[1].radius
        for (i in 0 until circles.size) {
            for (j in (i + 1) until circles.size) {
                val lengthBetweenCircles = circles[i].distance(circles[j])
                if (lengthBetweenCircles < min) {
                    min = lengthBetweenCircles
                    nearestCircle1 = circles[i]
                    nearestCircle2 = circles[j]
                }
            }
        }
        return Pair(nearestCircle1, nearestCircle2)
    }
}


/**
 * Сложная
 *
 * Дано три различные точки. Построить окружность, проходящую через них
 * (все три точки должны лежать НА, а не ВНУТРИ, окружности).
 * Описание алгоритмов см. в Интернете
 * (построить окружность по трём точкам, или
 * построить окружность, описанную вокруг треугольника - эквивалентная задача).
 */
fun circleByThreePoints(a: Point, b: Point, c: Point): Circle {
    val abX = a.x - b.x
    val abY = a.y - b.y
    val acX = a.x - c.x
    val acY = a.y - c.y
    val e = ((a.x - b.x) * (a.x + b.x) - (a.y + b.y) * (b.y - a.y)) / 2
    val f = ((a.x - c.x) * (a.x + c.x) - (a.y + c.y) * (c.y - a.y)) / 2
    val delta1 = abY * acX - abX * acY
    val x0 = (abX * e - abY * f) / delta1
    val y0 = (acX * e - abX * f) / delta1
    val center = Point(x0, y0)
    return Circle(center, a.distance(center))
}

/**
 * Очень сложная
 *
 * Дано множество точек на плоскости. Найти круг минимального радиуса,
 * содержащий все эти точки. Если множество пустое, бросить IllegalArgumentException.
 * Если множество содержит одну точку, вернуть круг нулевого радиуса с центром в данной точке.
 *
 * Примечание: в зависимости от ситуации, такая окружность может либо проходить через какие-либо
 * три точки данного множества, либо иметь своим диаметром отрезок,
 * соединяющий две самые удалённые точки в данном множестве.
 */
fun minContainingCircle(vararg points: Point): Circle {
    if (points.isEmpty()) throw IllegalArgumentException()
    if (points.size == 1) return Circle(points[0], 0.0)
    if (points.size == 2) return circleByDiameter(Segment(points[0], points[1]))
    val segment = diameter(*points)
    val start = segment.begin
    val end = segment.end
    val p = Point(Math.min(start.x, end.x) + Math.abs(start.x - end.x) / 2,
            Math.min(start.y, end.y) + Math.abs(start.y - end.y) / 2)
    val rad = start.distance(p)
    var maxDistance = rad
    var pointMax = start
    for (point in points) {
        if (point.distance(p) > maxDistance) {
            maxDistance = point.distance(p)
            pointMax = point
        }
    }
    return when {
        Math.abs(pointMax.distance(p)) - rad <= 1e-12 -> Circle(p, maxDistance)
        else -> circleByThreePoints(start, end, pointMax)
    }
}

