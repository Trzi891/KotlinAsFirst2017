@file:Suppress("UNUSED_PARAMETER")

package lesson5.task1

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде  строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main(args: Array<String>) {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        } else {
            println("Прошло секунд с начала суток: $seconds")
        }
    } else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку
 */
val months = listOf("января", "февраля", "марта", "апреля", "мая", "июня",
        "июля", "августа", "сентября", "октября", "ноября", "декабря")

fun dateStrToDigit(str: String): String {
    val parts = str.split(" ")
    if (parts.size != 3) return ""
    try {
        val day = parts[0].toInt()
        val month = parts[1]
        val year = parts[2].toInt()
        if (day !in 1..31 || month !in months) return ""
        return String.format("%02d.%02d.%d", day, months.indexOf(month) + 1, year)
    } catch (e: NumberFormatException) {
        return ""
    }

}


/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 */
fun dateDigitToStr(digital: String): String {
    val parts = digital.split(".")
    if (parts.size != 3) return ""
    try {
        val day = parts[0].toInt()
        val month = parts[1].toInt()
        val year = parts[2].toInt()
        if (day !in 1..31 || month !in 1..12) return ""
        return String.format("%d %s %d", day, months[month - 1], year)
    } catch (e: NumberFormatException) {
        return ""
    }
}

/**
 * Средняя
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -98 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку
 */
fun flattenPhoneNumber(phone: String): String {
    val phonee = phone.filter { it != ' ' && it != '-' }
    val result = Regex("""(?:\+\d+)?(?:\(\d+\))?\d+""")
    if (!result.matches(phonee)) return ""
    return phonee.filter { it != '(' && it != ')' }
}

/**
 * Средняя
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int {
    val parts = jumps.split(" ").filter { it != "" }
    var max = -1
    try {
        for (part in parts) {
            if (part != "%" && part != "-") {
                val number = part.toInt()
                if (max < number) max = number
            }
        }
    } catch (e: NumberFormatException) {
        return -1
    }
    return max
}

/**
 * Сложная
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки вернуть -1.
 */
fun bestHighJump(jumps: String): Int {
    var max = 0
    var count = 0
    val parts = jumps.split(" ")
    if (parts.size <= 1) return -1
    else {
        for (i in 0..(parts.size - 1) step 2) {
            val jumpregular = Regex("""\d+[-+%]+""")
            if (!jumpregular.matches(parts[i] + parts[i + 1])) return -1
            if ('+' in parts[i + 1] && parts[i].toInt() >= max) {
                max = parts[i].toInt()
                count++
            }
        }
    }
    if (count > 0) return max
    else return -1
}

/**
 * Сложная
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int {
    val sumString = Regex("""(?:(\d+\s+[-+]\s+)+)?\d+""")
    try {
        if (!sumString.matches(expression)) {
            throw IllegalArgumentException()
        } else {
            val parts = expression.split(" ")
            var sum = parts[0].toInt()
            for (i in 0 until (parts.size - 2) step 2) {
                if (parts[i + 1] == "+") {
                    sum += parts[i + 2].toInt()
                } else {
                    sum -= parts[i + 2].toInt()
                }
            }
            return sum
        }
    } catch (e: NumberFormatException) {
        throw IllegalArgumentException()
    }
}

/**
 * Сложная
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int {
    val parts = str.toLowerCase().split(" ").filter { it != "" }
    var result = 0
    for (i in 0 until parts.size - 1) {
        if (parts[i] == parts[i + 1]) return result
        result += parts[i].length + 1
    }
    return -1
}

/**
 * Сложная
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62.5; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть положительными
 */
fun mostExpensive(description: String): String {
    var result = ""
    try {
        val parts = description.split(";")
        var maxprice = 0.0
        for (element in parts) {
            val place = element.lastIndexOf(" ")
            val price = element.drop(place + 1).toDouble()
            if (price >= maxprice) {
                result = element.take(place)
                maxprice = price
            }
        }
    } catch (e: NumberFormatException) {
        return ""
    }
    return result.trim()
}

/**
 * Сложная
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
fun fromRoman(roman: String): Int {
    var result = 0
    if (!Regex("""[IVXLCDM]+""").matches(roman)) return -1
    else {
        val romans = mapOf("I" to 1, "V" to 5, "X" to 10, "L" to 50, "C" to 100, "D" to 500, "M" to 1000)
        for (i in 0 until roman.length) {
            when (roman[i]) {
                'M' -> {
                    if (i == 0) result += romans["M"]!!
                    else if (roman[i - 1] == 'C') result += 900
                    else result += romans["M"]!!
                }
                'D' -> {
                    if (i == 0) result += romans["D"]!!
                    else if (roman[i - 1] == 'C') result += 400
                    else result += romans["D"]!!
                }
                'C' -> {
                    if (i == 0) {
                        if (roman[i + 1] != 'D' && roman[i + 1] != 'M') result += romans["C"]!!
                    } else if (i == roman.length - 1) {
                        if (roman[i - 1] != 'X') result += 100 else result += 90
                    } else if ((roman[i - 1] != 'X') && (roman[i + 1] != 'D' && roman[i + 1] != 'M')) result += romans["C"]!!
                    else if (roman[i - 1] == 'X') result += 90
                }
                'L' -> {
                    if (i == 0) result += romans["L"]!!
                    else if (roman[i - 1] == 'X') result += 40
                    else result += romans["L"]!!
                }
                'X' -> {
                    if (i == 0) {
                        if (roman[i + 1] != 'L' && roman[i + 1] != 'C') result += romans["X"]!!
                    } else if (i == roman.length - 1) {
                        if (roman[i - 1] == 'I') result += 9
                        else result += romans["X"]!!
                    } else if (roman[i] == 'I') result += 9
                    else if (roman[i + 1] != 'L' && roman[i + 1] != 'C') result += romans["X"]!!
                }
                'V' -> {
                    if (i == 0) result += romans["V"]!!
                    else if (roman[i - 1] == 'I') result += 4
                    else result += romans["V"]!!
                }
                'I' -> {
                    if (i == roman.length - 1) result += romans["I"]!!
                    else if (roman[i + 1] != 'V' && roman[i + 1] != 'X') result += romans["I"]!!
                }
            }
        }
    }
    return result
}

/**
 * Очень сложная
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> = TODO()/*{
    val formal = Regex("""[+-><]+|\[\]""")
    try {
        if ( !formal.matches(commands) ){
            throw IllegalArgumentException("IllegalArgumentException")
        }
        else{

        }
}*/
