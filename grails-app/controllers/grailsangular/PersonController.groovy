package grailsangular

import groovy.sql.Sql
import static grails.async.Promises.task
import static grails.async.Promises.waitAll


class PersonController {

    def dataSource

    def index() {

        Sql sql = new Sql(dataSource)
        def sum = 0;

        def tasks = [1,2,3,4].collect{z->
            task{
                println "Calling Stored Procedure::: " + z

                if(z == 1){
                    def row = sql.rows("{call Proc_1()}");
                    sum = sum + Integer.parseInt(row[0].get("output").toString());
                }else if( z == 2){
                    def row = sql.rows("{call Proc_2()}");
                    sum = sum + Integer.parseInt(row[0].get("output").toString());
                }else if( z == 3){
                    def row = sql.rows("{call Proc_3()}");
                    sum = sum + Integer.parseInt(row[0].get("output").toString());
                }else if( z == 4){
                    def row = sql.rows("{call Proc_4()}");
                    sum = sum + Integer.parseInt(row[0].get("output").toString());
                }

            }
        }

        waitAll(tasks)

        render "SUM ::" + sum


//        def list = Person.getAll()
//        println("HERE!!!!")
//        [list:list]
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
