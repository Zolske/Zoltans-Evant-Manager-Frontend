package com.kepes.zoltanseventmanagerfrontend.data

/**
 * Quick solution for a calendar event, to be improved in the future
 * @param startDate: "yyyy-MM-dd" , format of the date, e.g. "2023-06-10",
 * must start with year (4 digits) + 1 delimiter + month (2 digits) + 1 delimiter + day (2 digits)
 * @param startTime: "hh:mm" , format of the time, e.g. "10:30",
 * must start with hour (2 digits) + 1 delimiter + minute (2 digits)
 * @param durationHour: duration of the event in hours, e.g. 2,
 * do not overflow the hour into the next day (startHour + durationHour <= 24)!
 * There is no validation for this.
 */
class CalDateTime(startDate: String, startTime: String, durationHour: Int) {
    val startYear: Int = startDate.substring(0, 4).toInt()
    val startMonth: Int = startDate.substring(5, 7).toInt() - 1 // Month is 0-based in Calendar
    val startDay: Int = startDate.substring(8, 10).toInt()
    val startHour: Int = startTime.substring(0, 2).toInt()
    val startMinute: Int = startTime.substring(3, 5).toInt()
    val endYear: Int = startYear
    val endMonth: Int = startMonth
    val endDay: Int = startDay
    val endHour: Int = startHour + durationHour
    val endMinute: Int = startMinute

    override fun toString(): String {
        return "$startYear-$startMonth-$startDay $startHour:$startMinute"
    }
}
