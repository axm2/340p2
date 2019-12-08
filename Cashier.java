package app;

public class Cashier implements Runnable {
    public int ID;
    public static int totalCustomersHelpedCashier;

    Cashier(int ID) {
        this.ID = ID;

    }

    @Override
    public void run() {
        msg("is waiting to help customers");
        if (ID == 0) {
            while (App.getCustomersHelpedCashier() < App.totalCustomers) {
                serveCash();
            }
        }
        if (ID == 1) {
            while (App.getCustomersHelpedCashier() < App.totalCustomers) {
                serveCredit();
            }
        }
        for (int i = 0; i < App.totalCashiers; i++) {
            if (i != ID) {
                App.cashierThreads[i].interrupt();
                //We should check if anyones waiting on the semaphore before interrupting
            }
        }
    }

    public void msg(String m) {
        System.out.println("[" + (System.currentTimeMillis() - App.time) + "] " + "Cashier-" + ID + ": " + m);
    }

    public void serveCash() {
        try {
            App.waitingPayCash.acquire();
            App.incCustomersHelpedCashier();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            return;
        }
    }

    public void serveCredit() {
        try {
            App.waitingPayCredit.acquire();
            App.incCustomersHelpedCashier();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            return;
        }
    }
}