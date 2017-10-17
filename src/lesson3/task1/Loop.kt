@file:Suppress("UNUSED_PARAMETER")
package lesson3.task1

import lesson1.task1.sqr
import lesson4.task1.abs
import sun.security.util.Length

/**
 * Пример
 *
 * Вычисление факториала
 */
fun factorial(n: Int): Double {
    var result = 1.0
    for (i in 1..n) {
        result = result * i // Please do not fix in master
    }
    return result
}

/**
 * Пример
 *
 * Проверка числа на простоту -- результат true, если число простое
 */
fun isPrime(n: Int): Boolean {
    if (n < 2) return false
    for (m in 2..Math.sqrt(n.toDouble()).toInt()) {
        if (n % m == 0) return false
    }
    return true
}

/**
 * Пример
 *
 * Проверка числа на совершенность -- результат true, если число совершенное
 */
fun isPerfect(n: Int): Boolean {
    var sum = 1
    for (m in 2..n/2) {
        if (n % m > 0) continue
        sum += m
        if (sum > n) break
    }
    return sum == n
}

/**
 * Пример
 *
 * Найти число вхождений цифры m в число n
 */
fun digitCountInNumber(n: Int, m: Int): Int =
        when {
            n == m -> 1
            n < 10 -> 0
            else -> digitCountInNumber(n / 10, m) + digitCountInNumber(n % 10, m)
        }

/**
 * Тривиальная
 *
 * Найти количество цифр в заданном числе n.
 * Например, число 1 содержит 1 цифру, 456 -- 3 цифры, 65536 -- 5 цифр.
 */
fun digitNumber(n: Int): Int {
    var count = 0
    var number = Math.abs( n )
    if ( n == 0 ) return 1
    while ( number >= 1 ){
        count++
        number /= 10
    }
    return count
}

/**
 * Простая
 *
 * Найти число Фибоначчи из ряда 1, 1, 2, 3, 5, 8, 13, 21, ... с номером n.
 * Ряд Фибоначчи определён следующим образом: fib(1) = 1, fib(2) = 1, fib(n+2) = fib(n) + fib(n+1)
 */
fun fib(n: Int): Int {
    val number = n
    var num1 = 1
    var num2 = 1
    var a = 0
    var d = 2
    if (n == 1 || n == 2 ) return 1
    for ( i in 1..n ){
        if ( d < number ){
            d ++
            a = num2
            num2 += num1
            num1 = a
        }
    }
    return num2
}
/**
 * Простая
 *
 * Для заданных чисел m и n найти наименьшее общее кратное, то есть,
 * минимальное число k, которое делится и на m и на n без остатка
 */
fun lcm(m: Int, n: Int): Int {
    var number = n
    var number2 = m
    while (( number2 > 0 ) && ( number > 0)){
        if( number > number2 ) number = number % number2
        else number2 = number2 % number
    }
    val x = (m * n) /( number + number2 )
    return x
}
/**
 * Простая
 *
 * Для заданного числа n > 1 найти минимальный делитель, превышающий 1
 */
fun minDivisor(n: Int): Int {
    var d = 2
    val number = n
    while ( number % d != 0  ) d ++
    return d
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти максимальный делитель, меньший n
 */
fun maxDivisor(n: Int): Int {
    val number = n
    var d = number - 1
    for ( i in 1..n){
        if ( number % d != 0 )
            d --
    }
    return  d
}

/**
 * Простая
 *
 * Определить, являются ли два заданных числа m и n взаимно простыми.
 * Взаимно простые числа не имеют общих делителей, кроме 1.
 * Например, 25 и 49 взаимно простые, а 6 и 8 -- нет.
 */
fun isCoPrime(m: Int, n: Int): Boolean = ( m * n ) / lcm( m , n ) == 1


/**
 * Простая
 *
 * Для заданных чисел m и n, m <= n, определить, имеется ли хотя бы один точный квадрат между m и n,
 * то есть, существует ли такое целое k, что m <= k*k <= n.
 * Например, для интервала 21..28 21 <= 5*5 -<= 28, а для интервала 51..61 квадрата не существует.
 */
fun squareBetweenExists(m: Int, n: Int): Boolean {
    var x = 0
    while( x * x in 0..n ){
        if(x * x in m..n)
            return true
        x ++
    }
    return false
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * sin(x) = x - x^3 / 3! + x^5 / 5! - x^7 / 7! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun sin(x: Double, eps: Double): Double {
    val base = x % ( 2 * Math.PI )
    var result = base
    var duoxiangshi = base
    var count = 0
    while ( Math.abs( duoxiangshi ) >= eps ){
        count ++
        duoxiangshi = Math.pow(base ,count.toDouble() * 2 + 1) / factorial(count * 2 + 1)
        if ( count % 2 == 0 )result += duoxiangshi
        else result -= duoxiangshi
    }
    return result
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * cos(x) = 1 - x^2 / 2! + x^4 / 4! - x^6 / 6! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun cos(x: Double, eps: Double): Double {
    val base = x % ( 2 * Math.PI )
    var duoxiangshi = base
    var count = 0
    var result = 1.0
    while ( Math.abs( duoxiangshi ) >= eps ){
        count ++
        duoxiangshi = Math.pow( base , count.toDouble() * 2 ) / factorial( 2 * count )
        if ( count % 2 == 1 ) result -= duoxiangshi
        else result += duoxiangshi
    }
    return result
}

/**
 * Средняя
 *
 * Поменять порядок цифр заданного числа n на обратный: 13478 -> 87431.
 * Не использовать строки при решении задачи.
 */
fun revert(n: Int): Int {
    var num = n
    var reversed = 0
    while (num != 0) {
        val digit = num % 10
        reversed = reversed * 10 + digit
        num /= 10
    }
    return reversed
}

/**
 * Средняя
 *
 * Проверить, является ли заданное число n палиндромом:
 * первая цифра равна последней, вторая -- предпоследней и так далее.
 * 15751 -- палиндром, 3653 -- нет.
 */
fun isPalindrome(n: Int): Boolean {
    val num = revert( n )
    return num == n
}

/**
 * Средняя
 *
 * Для заданного числа n определить, содержит ли оно различающиеся цифры.
 * Например, 54 и 323 состоят из разных цифр, а 111 и 0 из одинаковых.
 */
fun hasDifferentDigits(n: Int): Boolean {
    var num = n
    var lastnum = 0
    if ( Math.abs( n ) < 10 ) return false
    while ( num > 10 ) {
            lastnum = num % 10
            num /= 10
            if ( num % 10 != lastnum ) return true
        }
        return false
}
/* How to current it :
val num = n.toString()
    if ( num.length <= 1 ) return false
    var i = 0
    var j = 1
    while (i < num.length) {
            if (num.[i].toInt() != num.[j].toInt()) return true
            else{
                j++
                i++
            }
    }
    return false*/


/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из квадратов целых чисел:
 * 149162536496481100121144...
 * Например, 2-я цифра равна 4, 7-я 5, 12-я 6.
 */
fun squareSequenceDigit(n: Int): Int {
    var Length = 0L
    var num = 0L
    var numsqr = 0L
    while( Length < n ){
        num ++
        numsqr = num * num
        Length += digitNumber( numsqr.toInt() )
    }
    return when{
        Length == 1L -> 1
        Length.toInt() == n -> numsqr.toInt() % 10
        else -> {
            val delta = Length.toInt() - n
            var result = numsqr.toInt()
            for ( i in 1..delta ){
                result /= 10
            }
            return result.toInt() % 10
        }
    }
    /*val s = n.toString()
    val Length1 = Length.toString()
    var x = 0
    x = Length1.get( n ).toInt()
    return x*/
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из чисел Фибоначчи (см. функцию fib выше):
 * 1123581321345589144...
 * Например, 2-я цифра равна 1, 9-я 2, 14-я 5.
 */
fun fibSequenceDigit(n: Int): Int {
    var Length = 0L
    var num = 0L
    var fibnumber = 0L
    while( Length < n ){
        num++
        fibnumber = fib(num.toInt() ).toLong()
        Length += digitNumber( fibnumber.toInt() )
    }
    return when{
        Length.toInt() == 1 || Length.toInt() == 2 -> 1
        Length.toInt() == n -> fibnumber.toInt() % 10
        else -> {
            var result = fibnumber
            val delta = Length.toInt() - n
            for ( i in 1..delta ){
                result /= 10
            }
            return result.toInt() % 10
        }
    }
}
