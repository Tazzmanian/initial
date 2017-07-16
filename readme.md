# heroku & postgres
1. make registration
2. need to add you to collabors
3. creat backup and download
4. need to install postgresql
5. create db
6. set PGUSER and PGPASSWORD if the db have authentication
7. pg_restore.exe --no-owner -d <local_db> <db_file>
can omit step 6 
PGUSER=<> PGPASSWORD=<> pg_restore.exe --no-owner -d <local_db> <db_file>