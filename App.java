package app;

import java.util.concurrent.Semaphore;

public class App {
    public static long time = System.currentTimeMillis();
    static int totalCustomers;
    static int totalCustomersHelped;
    static int totalCustomersHelpedCashier;
    static int totalClerks;
    static int totalCashiers;
    static int minisize;
    static int customersDoneShopping;
    // Thread pointer arrays
    static Thread[] customerThreads;
    static Thread[] clerkThreads;
    static Thread[] cashierThreads;
    public static Semaphore semCustomers = new Semaphore(0);
    public static Semaphore semClerks = new Semaphore(0);
    public static Semaphore mutex = new Semaphore(1);
    public static Semaphore mutex2 = new Semaphore(1);
    public static Semaphore clerkMutex = new Semaphore(1);
    public static Semaphore closed = new Semaphore(0);
    public static Semaphore waitingPayCash = new Semaphore(0);
    public static Semaphore waitingPayCredit = new Semaphore(0);
    public static Semaphore doneShopping = new Semaphore(0);
    public static Semaphore group = new Semaphore(0);


    public static void main(String[] args) throws Exception {
        // Set default arguments
        totalCustomers = 15;
        totalClerks = 3;
        totalCashiers = 2;
        minisize = 4;
        // Replace defaults with command line arguments if they exist
        if (args.length != 0) {
            totalCustomers = Integer.parseInt(args[0]);
        }

        customerThreads = new Thread[totalCustomers];
        clerkThreads = new Thread[totalClerks];
        cashierThreads = new Thread[totalCashiers];

        for (int i = 0; i < totalCashiers; i++) {
            cashierThreads[i] = new Thread(new Cashier(i));
            cashierThreads[i].start();
        }
        for (int i = 0; i < totalClerks; i++) {
            clerkThreads[i] = new Thread(new Clerk(i));
            clerkThreads[i].start();
        }
        for (int i = 0; i < totalCustomers; i++) {
            customerThreads[i] = new Thread(new Customer(i));
            customerThreads[i].start();
        }

    }

    public static int getCustomersHelped() {
        int result;
        try {
            mutex.acquire();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }
        result = totalCustomersHelped;
        mutex.release();
        return result;

    }

    public static void incCustomersHelped() {
        try {
            mutex.acquire();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }
        totalCustomersHelped++;
        mutex.release();
        
    }

    public static int getCustomersHelpedCashier() {
        int result;
        try {
            mutex.acquire();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }
        result = totalCustomersHelpedCashier;
        mutex.release();
        return result;

    }

    public static void incCustomersHelpedCashier() {
        try {
            mutex.acquire();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }
        totalCustomersHelpedCashier++;
        mutex.release();
        
    }
    
    public static int getCustomersDoneShopping(){
        int result;
        try {
            mutex2.acquire();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }
        result = customersDoneShopping;
        mutex2.release();
        return result;
    }

    public static void incCustomersDoneShopping(){
        try {
            mutex2.acquire();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }
        customersDoneShopping++;
        mutex2.release();
    }
}