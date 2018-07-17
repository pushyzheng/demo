import os

DEBUG = True
SECRET_KEY = os.urandom(24)

DIALECT = 'mysql'
DRIVER = 'pymysql'
USERNAME = 'root'
PASSWORD = '123456'
HOST = 'localhost'
PORT = '3306'
DATABASE = 'demo'
SQLALCHEMY_DATABASE_URI = "{}+{}://{}:{}@{}:{}/{}?charset=utf8".format(DIALECT, DRIVER, USERNAME, PASSWORD, HOST, PORT,
                                                                       DATABASE)