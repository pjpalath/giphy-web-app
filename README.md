# giphy-web-app

This is a simple wrapper app for the GIPHY (https://giphy.com/) api. The app allows users to 
1. Register themselves as a new user (At this point the registration is just limited to uname/pwd and no email based registration)
2. Once logged in each user should have their own profile (A simple set of pages)
3. The header which is mostly static has a logout button that the user can use to log out of the system
4. While logging in the user has the option of chosing the "Remember Me" option - which would keep the user logged in
5. The "Fun With GIF's" tab is where all the magic lies - 
   a. Users can search for GIF's based on keyword
   b. They can then keep scrolling through a list of GIF's for tha search by using the next button
   c. If any GIF is of interest it can be added to the list of GIF's on their profile
   d. Users have the ability to classify a particular GIF that is addded to the profile and the capability to go back later and edit that classification or delete the GIF altogether from their profile.
   
Details of the source code and how to run it - 
1. The source code project is an eclipse (Dynamic Web Project) project. So it can be checked out and imported into eclipse
2. Apache Tomcat 7 was used to run the application by configuring the server runtime on eclipse
3. For the database, the application was stored on a MySQL database. The details of the database name and the tables is given below -
   a. Database - giphy
   b. Tables created/used (DDL) - 
   
      CREATE TABLE gif (
	    gifurl VARCHAR(200) NOT NULL,
	    giftype VARCHAR(100) NOT NULL,
	    PRIMARY KEY (gifurl)
      );

      CREATE TABLE user (
	    uname VARCHAR(50) NOT NULL,
	    pwd VARCHAR(50) NOT NULL,
	    Host CHAR(60) NOT NULL,
	    User CHAR(32) NOT NULL,
	    Select_priv ENUM DEFAULT N NOT NULL,
	    Insert_priv ENUM DEFAULT N NOT NULL,
	    Update_priv ENUM DEFAULT N NOT NULL,
	    Delete_priv ENUM DEFAULT N NOT NULL,
	    Create_priv ENUM DEFAULT N NOT NULL,
	    Drop_priv ENUM DEFAULT N NOT NULL,
	    Reload_priv ENUM DEFAULT N NOT NULL,
	    Shutdown_priv ENUM DEFAULT N NOT NULL,
	    Process_priv ENUM DEFAULT N NOT NULL,
	    File_priv ENUM DEFAULT N NOT NULL,
	    Grant_priv ENUM DEFAULT N NOT NULL,
	    References_priv ENUM DEFAULT N NOT NULL,
	    Index_priv ENUM DEFAULT N NOT NULL,
	    Alter_priv ENUM DEFAULT N NOT NULL,
	    Show_db_priv ENUM DEFAULT N NOT NULL,
	    Super_priv ENUM DEFAULT N NOT NULL,
	    Create_tmp_table_priv ENUM DEFAULT N NOT NULL,
	    Lock_tables_priv ENUM DEFAULT N NOT NULL,
	    Execute_priv ENUM DEFAULT N NOT NULL,
	    Repl_slave_priv ENUM DEFAULT N NOT NULL,
	    Repl_client_priv ENUM DEFAULT N NOT NULL,
	    Create_view_priv ENUM DEFAULT N NOT NULL,
	    Show_view_priv ENUM DEFAULT N NOT NULL,
	    Create_routine_priv ENUM DEFAULT N NOT NULL,
	    Alter_routine_priv ENUM DEFAULT N NOT NULL,
	    Create_user_priv ENUM DEFAULT N NOT NULL,
	    Event_priv ENUM DEFAULT N NOT NULL,
	    Trigger_priv ENUM DEFAULT N NOT NULL,
	    Create_tablespace_priv ENUM DEFAULT N NOT NULL,
	    ssl_type ENUM NOT NULL,
	    ssl_cipher BLOB NOT NULL,
	    x509_issuer BLOB NOT NULL,
	    x509_subject BLOB NOT NULL,
	    max_questions INTEGER UNSIGNED DEFAULT 0 NOT NULL,
	    max_updates INTEGER UNSIGNED DEFAULT 0 NOT NULL,
	    max_connections INTEGER UNSIGNED DEFAULT 0 NOT NULL,
	    max_user_connections INTEGER UNSIGNED DEFAULT 0 NOT NULL,
	    plugin CHAR(64) DEFAULT caching_sha2_password NOT NULL,
	    authentication_string TEXT,
	    password_expired ENUM DEFAULT N NOT NULL,
	    password_last_changed TIMESTAMP,
	    password_lifetime SMALLINT UNSIGNED,
	    account_locked ENUM DEFAULT N NOT NULL,
	    Create_role_priv ENUM DEFAULT N NOT NULL,
	    Drop_role_priv ENUM DEFAULT N NOT NULL,
	    Password_reuse_history SMALLINT UNSIGNED,
	    Password_reuse_time SMALLINT UNSIGNED,
	    PRIMARY KEY (Host,User)
    );

    CREATE TABLE gif_user (
	  gifurl VARCHAR(200) NOT NULL,
	  uname VARCHAR(30) NOT NULL
    );

    CREATE UNIQUE INDEX gif_user_unique ON gif_user (gifurl ASC);

    CREATE UNIQUE INDEX gif_user_unique ON gif_user (uname ASC);

    CREATE INDEX gif_user_user ON gif_user (uname ASC);

    ALTER TABLE user ADD UNIQUE (uname);
