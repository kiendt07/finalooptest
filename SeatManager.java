/**
 * Created by j on 05/06/2016.
 */
public class SeatManager {
    private static Seat freeSeats;
    private static Seat occupiedSeats;

    public static long MAXTIME;
    public static int MAXSEAT;

    public SeatManager (long time, int seat) {
        this.MAXTIME = time;
        this.MAXSEAT = seat;
        this.freeSeats = new Seat();
        this.occupiedSeats = new Seat();
        for (int i=1; i<=MAXSEAT; i++) {
            this.freeSeats.insertBefore(new Seat("", 0));
        }
    }

    public Seat checkin (String userId) {
        Seat userSeat = findUser(userId);
        if (userSeat != null) {
            System.out.println("\nChecking in USER " + userId + "\n" +
                    "FAILED! USER " + userId + " has already checked in.");
            return userSeat;
        }

        // User chua su dung cho :
        if (freeSeats.hasAvailable()) {
            userSeat = getFreeSeat();
            userSeat.setUserId(userId);
            userSeat.setCheckInTime(System.currentTimeMillis());

            System.out.println("\nChecking in user " + userId +
                    "\nSUCCESSED! FREE SEAT " + userSeat.getSeatNumber());
            return (Seat) occupiedSeats.prev().insertBefore(userSeat);
        }

        // Het freeSeats, lay cho ngoi da qua thoi gian trong occupiedSeats
        if (occupiedSeats.hasVacation()) {
            userSeat = occupiedSeats.popAvailable();
            userSeat.setUserId(userId);
            userSeat.setCheckInTime(System.currentTimeMillis());

            System.out.println("\nChecking in user " + userId +
                    "\nSUCCESSED! VACATIONAL SEAT " + userSeat.getSeatNumber());
            return (Seat) occupiedSeats.prev().insertBefore(userSeat);
        }

        // Ko con cho ngoi nao
        System.out.println("\nChecking in user " + userId + "" +
                "\nFAILED! All seats are not available.");
        return null;
    }

    public boolean checkout (String userId) {
        Seat userSeat = findUser(userId);
        if (userSeat == null) {
            System.out.println("\nChecking out user " + userId +
                    "\nFAILED! User + " + userId + " hasn't checked in yet.");
            return false;
        }

        // Xoa userSeat trong occupiedSeats, insert vao freeSeats
        freeSeats.insertBefore(userSeat.remove());
        System.out.println("\nChecking out user " + userId +
                "\nSUCCESSED - SEAT " + userSeat.getSeatNumber() + " is now available.");
        return true;
    }

    private Seat findUser (String userId) {
        Seat seat = occupiedSeats.next();
        while (!seat.equals(occupiedSeats)) {
            if (seat.getUserId().equals(userId)) return seat;
            seat = seat.next();
        }

        return null;
    }

    private Seat getFreeSeat() {
        return freeSeats.popAvailable();
    }

    public static void main(String args[]) {
        // Ngoi toi da 2s, co 2 cho ngoi
        SeatManager manager = new SeatManager(2000, 2);
        manager.checkin("1"); // SUCCESS

        manager.checkout("1"); // SUCCESS

        manager.checkout("2"); // FAIL, not checked in yet

        manager.checkin("2"); // SUCCESS

        System.out.println("\nWait 2s...");
        // Wain 2s, then check in some students
        try {
            Thread.sleep(2000);
        } catch (Exception e){

        }

        manager.checkin("2"); // FAIL, already checked in

        manager.checkin("3"); // SUCCESS

        manager.checkin("4"); // SUCCESS

        System.out.println("\nWait 1s...");
        // Wait 1s, then check in some students
        try {
            Thread.sleep(1000);
        } catch (Exception e){
        }

        manager.checkin("5"); // FAIL, all seats are filled

        System.out.println("\nWait 1s...");
        // Wait 1s
        try {
            Thread.sleep(1000);
        } catch (Exception e){
        }

        manager.checkin("5"); // SUCCESS
    }
}
