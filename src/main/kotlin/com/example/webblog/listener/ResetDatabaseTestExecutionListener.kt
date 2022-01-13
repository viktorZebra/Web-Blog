import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.TestContext
import org.springframework.test.context.support.AbstractTestExecutionListener
import java.sql.SQLException
import javax.sql.DataSource

class ResetDatabaseTestExecutionListener : AbstractTestExecutionListener() {
    @Autowired
    private val dataSource: DataSource? = null

    override fun getOrder(): Int {
        return 2001
    }

    override fun beforeTestClass(testContext: TestContext) {
        testContext.getApplicationContext()
            .getAutowireCapableBeanFactory()
            .autowireBean(this)
    }

    @Throws(Exception::class)
    override fun prepareTestInstance(testContext: TestContext) {
        cleanupDatabase()
    }

    @Throws(Exception::class)
    override fun afterTestClass(testContext: TestContext) {
        //cleanupDatabase()
    }

    @Throws(SQLException::class)
    private fun cleanupDatabase() {
        val c = dataSource!!.connection
        val s = c.createStatement()
        println("\nCLEANUP STARTED\n")

        // Disable FK
        s.execute("SET CONSTRAINTS ALL DEFERRED")

        // Find all tables and truncate them
        val tables: MutableSet<String> = HashSet()
        var rs = s.executeQuery("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES  where TABLE_SCHEMA='public'")
        println("\ntables was selected for truncate")
        while (rs.next()) {
            tables.add(rs.getString(1))
        }
        rs.close()
        for (table in tables) {
            s.executeUpdate("TRUNCATE TABLE $table CASCADE")
            println("truncated table $table")
        }

        // Idem for sequences
        val sequences: MutableSet<String> = HashSet()
        rs = s.executeQuery("SELECT SEQUENCE_NAME FROM INFORMATION_SCHEMA.SEQUENCES WHERE SEQUENCE_SCHEMA='public'")
        while (rs.next()) {
            sequences.add(rs.getString(1))
        }
        rs.close()
        for (seq in sequences) {
            s.executeUpdate("ALTER SEQUENCE $seq RESTART WITH 1")
        }

        // Enable FK
        //s.execute("SET REFERENTIAL_INTEGRITY TRUE")
        s.close()
        c.close()
        println("\nCLEANUP FINISHED\n")
    }
}