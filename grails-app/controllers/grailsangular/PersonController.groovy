package grailsangular

import groovy.sql.Sql

import static grails.async.Promises.task

class PersonController {

    def dataSource

    def index() {

        Sql sql = new Sql(dataSource)
        def sum = 0;

        println "Main Thread ::" + Thread.currentThread()

        def p = task {
            // Long running task
            println 'Off to do something now ... ' + Thread.currentThread()
            def row = sql.rows("{call Proc_1()}");
            println 'Called Stored procedure'
            sum = sum + Integer.parseInt(row[0].get("output").toString());
            return sum
        }

        p.onError { Throwable err ->
            println "An error occured ${err.message}"
        }
        p.onComplete { result ->
            println "THREAD HERE !!!!::" + Thread.currentThread()
            println "Promise returned : " + result
            render "Result::" + result;
        }
        println 'Prove the task is running in the background.'
        def res = p.get()
    }

    def newPerson(){
        //form page
    }

    def save(){
        println "Parameters::" + params
        def p = new Person(params)
        p.save()
        render "successfully added"
    }
}
