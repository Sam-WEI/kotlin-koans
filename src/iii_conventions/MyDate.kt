package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int): Comparable<MyDate> {
//    mine
//    override fun compareTo(other: MyDate): Int {
//        if (this.year != other.year) {
//            return this.year - other.year
//        }
//
//        if (this.month != other.month) {
//            return this.month - other.month
//        }
//
//        if (this.dayOfMonth != other.dayOfMonth) {
//            return this.dayOfMonth - other.dayOfMonth
//        }
//
//        return 0
//    }

    override fun compareTo(other: MyDate): Int = when {
        this.year != other.year -> this.year - other.year
        this.month != other.month -> this.month - other.month
        else -> this.dayOfMonth - other.dayOfMonth
    } // good!
}

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

class DateRange(override val start: MyDate, override val endInclusive: MyDate) : ClosedRange<MyDate>, Iterable<MyDate> {
    override fun iterator(): Iterator<MyDate> = DateIterator(this)

    override operator fun contains(other: MyDate) = start <= other && other <= endInclusive
}

class DateIterator(private val dateRange: DateRange): Iterator<MyDate> {
    var curr = dateRange.start
    override fun hasNext() = curr <= dateRange.endInclusive

    override fun next(): MyDate {
        val result = curr;
        curr = curr.nextDay()
        return result
    }
}

class RepeatedTimeInterval(val ti: TimeInterval, val n: Int)

operator fun MyDate.plus(ti: TimeInterval) = this.addTimeIntervals(ti, 1)

operator fun MyDate.plus(rti: RepeatedTimeInterval) = this.addTimeIntervals(rti.ti, rti.n)

operator fun TimeInterval.times(n: Int) = RepeatedTimeInterval(this, n)