# HYRISE JDBC Driver


This project is a very simple and naive implementation of a JDBC driver for
HYRISE. It allows to wrap the whol connection and querying process in the JDBC
infrastructure but still use Hyrise's JSON queries. It basically can be seen
as a Class 4 JDBC driver.


## Example Usage:

The following code snippet shows a very simple example on how to use the driver:

    Class.forName("org.hyrise.jdbc.HyriseDriver");
    java.sql.Connection c = DriverManager.getConnection("http://192.168.31.39:5001");
    java.sql.Statement s = c.createStatement();
    java.sql.ResultSet res = s.executeQuery("{\"priority\":1,\"performance\": false,\"operators\":{\"0\":{\"type\": \"NoOp\"}},\"edges\": [[\"0\", \"0\"]]}");

The above code will create a new connection to the HYRISE server and will then
create a new Statement object that can be executed. A Statement can only be
used once. There is as well support for prepared statements, the string for
the prepared statements must follow the Java String format rules.

    Class.forName("org.hyrise.jdbc.HyriseDriver");
    java.sql.Connection c = DriverManager.getConnection("http://192.168.31.39:5001");
    java.sql.PreparedStatement s = c.prepareStatement("{\"priority\":%1$s,\"performance\": %1$s, \"operators\":{\"0\":{\"type\": \"NoOp\"}},\"edges\": [[\"0\", \"0\"]]}");
    s.setInt(0, 1);
    s.setBoolean(1, true);
    java.sql.ResultSet res = s.executeQuery();
    