package hr.algebra.eventmanagement.helpers


fun String.Companion.toParcelableDate(year: String, month: String, day: String): String {
    return if (month.toInt() < 10 && day.toInt() < 10) {
        "${year}-0${month}-0${day}"
    } else if (month.toInt() < 10) {
        "${year}-0${month}-${day}"
    } else if (day.toInt() < 10) {
        "${year}-${month}-0${day}"
    } else {
        "${year}-${month}-${day}"
    }
}