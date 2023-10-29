public enum Meseci {
        JANUARY(0),
        FEBRUARY(1),
        MARCH(2),
        APRIL(3),
        MAY(4),
        JUNE(5),
        JULY(6),
        AUGUST(7),
        SEPTEMBER(8),
        OCTOBER(9),
        NOVEMBER(10),
        DECEMBER(11);

        private int monthNumber;

    Meseci(int monthNumber) {
            this.monthNumber = monthNumber;
        }

        public int getMonthNumber() {
            return monthNumber;
        }

        public static Meseci fromMonthNumber(int monthNumber) {
            for (Meseci month : values()) {
                if (month.monthNumber == monthNumber) {
                    return month;
                }
            }
            throw new IllegalArgumentException("Invalid month number: " + monthNumber);
        }

}
