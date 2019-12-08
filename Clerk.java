package app;

public class Clerk implements Runnable {
    public int ID;

    Clerk(int ID) {
        this.ID = ID;
    }

    @Override
    public void run() {
        while (App.getCustomersHelped() < App.totalCustomers) {
            App.semClerks.release();
            msg("waiting for a customer");
            try {
                App.semCustomers.acquire();
                msg("got customer");
                App.incCustomersHelped();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                // e.printStackTrace();
            }
        }
        // interrupt other 2
        for (int i = 0; i < App.totalClerks; i++) {
            if (i != ID) {
                App.clerkThreads[i].interrupt(); // We should check if anyones waiting on the semaphore before interrupting
            }
        }
        try {
            App.closed.acquire();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }

    }

    public void msg(String m) {
        System.out.println("[" + (System.currentTimeMillis() - App.time) + "] " + "Clerk-" + ID + ": " + m);
    }
}