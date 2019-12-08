package app;

public class Customer implements Runnable {
    public int ID;
    public boolean hasSlip;
    public static int joinNum;

    Customer(int ID) {
        this.ID = ID;
        hasSlip = false;
        joinNum = App.totalCustomers - 1;
    }

    @Override
    public void run() {
        try {
            Thread.sleep((long) (Math.random() * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        App.semCustomers.release();
        msg("waiting for clerk");
        try {
            App.semClerks.acquire();
            msg("got clerk");
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        msg("got a slip");
        pay(Math.random() < 0.5);
        //closing time!
        App.closed.release();

    }

    public void msg(String m) {
        System.out.println("[" + (System.currentTimeMillis() - App.time) + "] " + "Customer-" + ID + ": " + m);
    }

    public void pay(boolean n) {
        if (n) {
            App.waitingPayCash.release();
            msg("has decided to pay with cash");
            try {
                Thread.sleep((long) (Math.random() * 1));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
        } else {
            App.waitingPayCredit.release();
            msg("has decided to pay with credit");
            try {
                Thread.sleep((long) (Math.random() * 1));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}