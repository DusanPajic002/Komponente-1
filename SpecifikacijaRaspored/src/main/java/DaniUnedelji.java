public enum DaniUnedelji {
        SUNDAY(1),
        MONDAY(2),
        TUESDAY(3),
        WEDNESDAY(4),
        THURSDAY(5),
        FRIDAY(6),
        SATURDAY(7);

        private int dayNumber;

        DaniUnedelji(int dayNumber) {
            this.dayNumber = dayNumber;
        }

        public int getDayNumber() {
            return dayNumber;
        }

        public static DaniUnedelji fromDayNumber(int dayNumber) {
            for (DaniUnedelji day : values()) {
                if (day.dayNumber == dayNumber) {
                    return day;
                }
            }
            throw new IllegalArgumentException("Invalid day number: " + dayNumber);
        }

}
