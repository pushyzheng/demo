# encoding:utf-8
import json
import requests

class Request():

    @staticmethod
    def get(url, params = None, to_dict = True):
        resp = requests.get(url, params).text
        if to_dict:
            if resp.find('callback') > -1:
                pos_lb = resp.find('{')
                pos_rb = resp.find('}')
                resp = resp[pos_lb:pos_rb + 1]
            return json.loads(resp, encoding='utf-8')
        return resp

    @staticmethod
    def post(url, body, to_dict = True):
        resp = requests.post(
            url,
            json.dumps(body)
        ).text
        if to_dict:
            return json.loads(resp)
        return resp