/**
 * Created by j on 05/06/2016.
 */
public class Seat extends ListElement {
    private int seatNumber;
    private String userId;
    private long checkInTime;
    private static int NSEATS;

    public Seat () {
        super();
    }

    public Seat(String userId, long checkInTime) {
        super();
        this.seatNumber = ++NSEATS;
        this.userId = userId;
        this.checkInTime = checkInTime;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public Seat next () {
        return (Seat) this.nextElement();
    }

    public Seat prev () {
        return (Seat) this.prevElement();
    }

    public Seat popAvailable() {
        return hasAvailable()
                ? (Seat) this.next().remove()
                : null;
    }

    public boolean hasAvailable () {
        return isEmpty()
                ? false
                : true;
    }

    public boolean hasVacation () {
        Seat mostTimeSeat = !isEmpty()
                ? next()
                : null;
        return System.currentTimeMillis() - mostTimeSeat.getCheckInTime() > SeatManager.MAXTIME
                ? true
                : false;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(long checkInTime) {
        this.checkInTime = checkInTime;
    }
}
