package labo.hirarins.legacy.app.batch.dao;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Hashtable;
import java.util.Properties;
import java.util.logging.Logger;

import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameClassPair;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;
import javax.sql.DataSource;


import labo.hirarins.legacy.app.batch.BatchPropertyLoader;
import labo.hirarins.legacy.app.batch.BatchRuntimeException;

public class BatchInitialContextFactory implements InitialContextFactory {

    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(BatchInitialContextFactory.class.getName());


    public Context getInitialContext(Hashtable<?, ?> environment) throws NamingException {
        log.info("called BatchInitialContextFactory#getInitialContext");
        return new BatchInitialContext();
    }

    private static class BatchDataSource implements DataSource {

        private Properties props;

        public BatchDataSource() {
            try {
                this.props = BatchPropertyLoader.load("/batch.properties");
                log.info("props=" + props);
                Class.forName("org.postgresql.Driver");
            } catch(IOException e) {
                throw new BatchRuntimeException("load batch property error occured.", e);
            } catch (ClassNotFoundException e) {
                throw new BatchRuntimeException("load jdbc driver error occured.", e);
            }
        }
        public PrintWriter getLogWriter() throws SQLException {
            // unsupported
            throw new UnsupportedOperationException();
        }

        public void setLogWriter(PrintWriter out) throws SQLException {
            // unsupported
            throw new UnsupportedOperationException();
        }

        public void setLoginTimeout(int seconds) throws SQLException {
            // unsupported
            throw new UnsupportedOperationException();
        }

        public int getLoginTimeout() throws SQLException {
            // unsupported
            throw new UnsupportedOperationException();
        }

        public Logger getParentLogger() throws SQLFeatureNotSupportedException {
            // unsupported
            throw new UnsupportedOperationException();
        }

        public <T> T unwrap(Class<T> iface) throws SQLException {
            // unsupported
            throw new UnsupportedOperationException();
        }

        public boolean isWrapperFor(Class<?> iface) throws SQLException {
            // unsupported
            throw new UnsupportedOperationException();
        }

        public Connection getConnection() throws SQLException {
            Connection conn = DriverManager.getConnection(props.getProperty("db.url"), props.getProperty("db.user"), props.getProperty("db.pass"));
            conn.setAutoCommit(false);
            return conn;
		}

		public Connection getConnection(String username, String password) throws SQLException {
            // unsupported
            throw new UnsupportedOperationException();
		}

    }

    private static class BatchInitialContext implements Context {

        public Object lookup(Name name) throws NamingException {
            // unsuport method
            throw new UnsupportedOperationException();
        }

        public Object lookup(String name) throws NamingException {
            if ("java:comp/env/jdbc/labo-db".equals(name)) {
                return new BatchDataSource();
            }
            // unsuport method
            throw new UnsupportedOperationException();
        }

        public void bind(Name name, Object obj) throws NamingException {
            // nothing to do
        }

        public void bind(String name, Object obj) throws NamingException {
            // nothing to do
        }

        public void rebind(Name name, Object obj) throws NamingException {
            // nothing to do
        }

        public void rebind(String name, Object obj) throws NamingException {
            // nothing to do
        }

        public void unbind(Name name) throws NamingException {
            // nothing to do
        }

        public void unbind(String name) throws NamingException {
            // nothing to do
        }

        public void rename(Name oldName, Name newName) throws NamingException {
            // nothing to do
        }

        public void rename(String oldName, String newName) throws NamingException {
            // nothing to do
        }

        public NamingEnumeration<NameClassPair> list(Name name) throws NamingException {
            // unsupported
            throw new UnsupportedOperationException();
        }

        public NamingEnumeration<NameClassPair> list(String name) throws NamingException {
            // unsupported
            throw new UnsupportedOperationException();
        }

        public NamingEnumeration<Binding> listBindings(Name name) throws NamingException {
            // unsupported
            throw new UnsupportedOperationException();
        }

        public NamingEnumeration<Binding> listBindings(String name) throws NamingException {
            // unsupported
            throw new UnsupportedOperationException();
        }

        public void destroySubcontext(Name name) throws NamingException {
            // nothing to do
        }

        public void destroySubcontext(String name) throws NamingException {
            // nothing to do
        }

        public Context createSubcontext(Name name) throws NamingException {
            // unsupported
            throw new UnsupportedOperationException();
        }

        public Context createSubcontext(String name) throws NamingException {
            // unsupported
            throw new UnsupportedOperationException();
        }

        public Object lookupLink(Name name) throws NamingException {
            // unsupported
            throw new UnsupportedOperationException();
        }

        public Object lookupLink(String name) throws NamingException {
            // unsupported
            throw new UnsupportedOperationException();
        }

        public NameParser getNameParser(Name name) throws NamingException {
            // unsupported
            throw new UnsupportedOperationException();
        }

        public NameParser getNameParser(String name) throws NamingException {
            // unsupported
            throw new UnsupportedOperationException();
        }

        public Name composeName(Name name, Name prefix) throws NamingException {
            // unsupported
            throw new UnsupportedOperationException();
        }

        public String composeName(String name, String prefix) throws NamingException {
            // unsupported
            throw new UnsupportedOperationException();
        }

        public Object addToEnvironment(String propName, Object propVal) throws NamingException {
            // unsupported
            throw new UnsupportedOperationException();
        }

        public Object removeFromEnvironment(String propName) throws NamingException {
            // unsupported
            throw new UnsupportedOperationException();
        }

        public Hashtable<?, ?> getEnvironment() throws NamingException {
            // unsupported
            throw new UnsupportedOperationException();
        }

        public void close() throws NamingException {
            // nothing to do
		}

		public String getNameInNamespace() throws NamingException {
            // unsupported
            throw new UnsupportedOperationException();
		}
        

    }
    
}