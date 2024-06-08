// Task 1 of making the Online reservation system
// here we maked the train ticket booking System
import java.util.*;

public class Task1 {

    private static final int min = 1000;
    private static final int max = 9999;

    // Class to represent a User with username and password
    public static class User {
        private String username;
        private String password;
        Scanner sc = new Scanner(System.in);

        public User() {
            // Constructor
        }

        // Method to get username from user input
        public String getUsername() {
            System.out.println("Enter Username: ");
            username = sc.nextLine();
            return username;
        }

        // Method to get password from user input
        public String getPassword() {
            System.out.println("Enter Password: ");
            password = sc.nextLine();
            return password;
        }
    }

    // Class to represent a PNR (Passenger Name Record) for train reservations
    public static class PnrRecord {
        private int pnrNumber;
        private String passengerName;
        private String trainNumber;
        private String classType;
        private String journeyDate;
        private String from;
        private String to;
        Scanner sc = new Scanner(System.in);

        // Constructor generates a PNR number upon creation
        public PnrRecord() {
            this.pnrNumber = generatePnrNumber();
        }

        // Method to generate a random PNR number
        private int generatePnrNumber() {
            Random random = new Random();
            return random.nextInt(max - min + 1) + min;
        }

        // Method to input PNR details from user
        public void inputDetails() {
            System.out.println("Enter the passenger name: ");
            passengerName = sc.nextLine();
            System.out.println("Enter the train number: ");
            trainNumber = sc.nextLine();
            System.out.println("Enter the class type: ");
            classType = sc.nextLine();
            System.out.println("Enter the journey date (YYYY-MM-DD): ");
            journeyDate = sc.nextLine();
            System.out.println("Enter the starting place: ");
            from = sc.nextLine();
            System.out.println("Enter the destination place: ");
            to = sc.nextLine();
        }

        public int getPnrNumber() {
            return pnrNumber;
        }

        public String getPassengerName() {
            return passengerName;
        }

        public String getTrainNumber() {
            return trainNumber;
        }

        public String getClassType() {
            return classType;
        }

        public String getJourneyDate() {
            return journeyDate;
        }

        public String getFrom() {
            return from;
        }

        public String getTo() {
            return to;
        }

        // Method to represent PNR details as a string
        @Override
        public String toString() {
            return "PNR Number: " + pnrNumber +
                   "\nPassenger Name: " + passengerName +
                   "\nTrain Number: " + trainNumber +
                   "\nClass Type: " + classType +
                   "\nJourney Date: " + journeyDate +
                   "\nFrom: " + from +
                   "\nTo: " + to;
        }
    }

    // Class to represent a Reservation with an associated PNR record
    public static class Reservation {
        private int id;
        private String name;
        private String date;
        private int numberOfGuests;
        private PnrRecord pnrRecord;

        public Reservation(int id, String name, String date, int numberOfGuests, PnrRecord pnrRecord) {
            this.id = id;
            this.name = name;
            this.date = date;
            this.numberOfGuests = numberOfGuests;
            this.pnrRecord = pnrRecord;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getDate() {
            return date;
        }

        public int getNumberOfGuests() {
            return numberOfGuests;
        }

        public PnrRecord getPnrRecord() {
            return pnrRecord;
        }
    }

    // Class to manage the reservations
    public static class ReservationSystem {
        private List<Reservation> reservations = new ArrayList<>();
        private int nextId = 1;

        // Method to make a new reservation and add it to the list
        public Reservation makeReservation(String name, String date, int numberOfGuests, PnrRecord pnrRecord) {
            Reservation reservation = new Reservation(nextId++, name, date, numberOfGuests, pnrRecord);
            reservations.add(reservation);
            return reservation;
        }

        // Method to get the list of all reservations
        public List<Reservation> getReservations() {
            return reservations;
        }

        // Method to get a reservation by its ID
        public Reservation getReservationById(int id) {
            for (Reservation reservation : reservations) {
                if (reservation.getId() == id) {
                    return reservation;
                }
            }
            return null;
        }

        // Method to cancel a reservation by its ID
        public boolean cancelReservation(int id) {
            Reservation reservation = getReservationById(id);
            if (reservation != null) {
                reservations.remove(reservation);
                return true;
            }
            return false;
        }
    }

    // Class to provide a user interface for the reservation system
    public static class ReservationSystemUI {
        private ReservationSystem reservationSystem = new ReservationSystem();

        // Method to start the user interface
        public void start() {
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("1. Make a reservation");
                System.out.println("2. View all reservations");
                System.out.println("3. Cancel a reservation");
                System.out.println("4. Exit");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        System.out.print("Name: ");
                        String name = scanner.nextLine();
                        System.out.print("Date: ");
                        String date = scanner.nextLine();
                        System.out.print("Number of guests: ");
                        int numberOfGuests = scanner.nextInt();
                        scanner.nextLine();

                        // Create a new PnrRecord and input its details
                        PnrRecord pnrRecord = new PnrRecord();
                        pnrRecord.inputDetails();

                        // Make a reservation with the given details
                        Reservation reservation = reservationSystem.makeReservation(name, date, numberOfGuests, pnrRecord);
                        System.out.println("Reservation made with ID " + reservation.getId());
                        System.out.println(pnrRecord);
                        break;
                    case 2:
                        // Display all reservations
                        System.out.println("Reservations:");
                        for (Reservation r : reservationSystem.getReservations()) {
                            System.out.println("Reservation ID: " + r.getId() +
                                               "\nName: " + r.getName() +
                                               "\nDate: " + r.getDate() +
                                               "\nNumber of Guests: " + r.getNumberOfGuests() +
                                               "\nPNR Details:\n" + r.getPnrRecord() + "\n");
                        }
                        break;
                    case 3:
                        // Cancel a reservation by its ID
                        System.out.print("Reservation ID to cancel: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();

                        if (reservationSystem.cancelReservation(id)) {
                            System.out.println("Reservation canceled");
                        } else {
                            System.out.println("Reservation not found");
                        }
                        break;
                    case 4:
                        // Exit the program
                        return;
                    default:
                        System.out.println("Invalid choice");
                }

                System.out.println();
            }
        }
    }

    // Main method to start the reservation system UI
    public static void main(String[] args) {
        ReservationSystemUI ui = new ReservationSystemUI();
        ui.start();
    }
}

// Now its Output Time.....
