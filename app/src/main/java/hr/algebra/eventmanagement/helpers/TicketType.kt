package hr.algebra.eventmanagement.helpers

enum class TicketType(val value: Int) {
    GeneralAdmission(0), VIP(1), Press(2), EarlyBird(3);

    companion object {
        fun fromInt(value: Int) = values().first { it.value == value }
    }
}