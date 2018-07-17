# encoding:utf-8
import json

class Response():

    @staticmethod
    def success(data):
        return {
            'message': '',
            'data': data,
            'code': 200
        }

    @staticmethod
    def error(errno, errmsg):
        return {
            'message': errmsg,
            'data': None,
            'code': errno
        }