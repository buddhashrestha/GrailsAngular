package grailsangular

import groovy.sql.Sql

class PersonController {

    def dataSource

    static counter = 0;

    def index() {

        println "Current Thread:::" + Thread.currentThread().toString()

        Sql sql = new Sql(dataSource)
        def sum = 0;

        def row = sql.rows("{call Proc_1()}");
        sum = sum + Integer.parseInt(row[0].get("output").toString());


        counter++;
        println "Count::: " + counter
        render sum
    }
}
