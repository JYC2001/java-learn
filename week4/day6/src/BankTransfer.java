// 核心：统一锁顺序（所有线程按相同顺序获取账户锁），避免死锁
public class BankTransfer {
    // 账户类
    static class Account {
        private final String accountId; // 账户ID（唯一）
        private int balance; // 账户余额

        public Account(String accountId, int balance) {
            this.accountId = accountId;
            this.balance = balance;
        }

        // 转账方法（核心：统一锁顺序）
        public void transfer(Account target, int amount) throws InterruptedException {
            // 1. 获取两个账户的hashCode，按hashCode从小到大排序，确定锁顺序
            Account firstLock = this.hashCode() < target.hashCode() ? this : target;
            Account secondLock = this.hashCode() > target.hashCode() ? this : target;

            // 2. 按固定顺序获取锁
            synchronized (firstLock) {
                synchronized (secondLock) {
                    // 校验余额
                    if (this.balance < amount) {
                        System.out.println("账户" + this.accountId + "余额不足，转账失败");
                        return;
                    }
                    // 执行转账
                    this.balance -= amount;
                    target.balance += amount;
                    System.out.println("账户" +
                            this.accountId + "向账户" + target.accountId + "转账" + amount + "元，转账后："
                            + this.accountId + "余额=" + this.balance + "，" + target.accountId + "余额=" + target.balance);

                }
            }
        }

        public String getAccountId() {
            return accountId;
        }

        public int getBalance() {
            return balance;
        }
    }

    // 测试类（两个账户互相转账，无死锁）
    public static void main(String[] args) {
        Account accountA = new Account("A", 1000);
        Account accountB = new Account("B", 1000);

        // 线程1：A向B转账100元
        new Thread(() -> {
            try {
                accountA.transfer(accountB, 100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "转账线程1").start();

        // 线程2：B向A转账200元
        new Thread(() -> {
            try {
                accountB.transfer(accountA, 200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "转账线程2").start();
    }
}
