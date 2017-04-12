package epam.db.dao.implementation;

import epam.connection.ConnectionProxy;
import epam.connection.DataSource;

public class TransactionHelper {

    private static ThreadLocal<ConnectionProxy> connectionProxy = new ThreadLocal<>();
    private static TransactionHelper transactionHelper = new TransactionHelper();

    private TransactionHelper() {
    }

    public static TransactionHelper getInstance() {
        return transactionHelper;
    }

    ConnectionProxy getConnectionProxy() {
        ConnectionProxy proxy = connectionProxy.get();
        if (proxy == null) {
            proxy = DataSource.getInstance().getConnection();
        }
        return proxy;
    }

    public void beginTransaction() {
        ConnectionProxy proxy = DataSource.getInstance().getConnection();
        proxy.setTransactionActive(true);
        proxy.setAutoCommit(false);
        connectionProxy.set(proxy);
    }

    public void commit() {
        ConnectionProxy proxy = connectionProxy.get();
        connectionProxy.set(null);
        proxy.commit();
        proxy.setTransactionActive(false);
        proxy.close();
    }

    public void rollback() {
        connectionProxy.get().rollback();
    }
}
