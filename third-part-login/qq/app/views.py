# encoding:utf-8
import re
from app import app,db
from util import urls, Response, Request, logger
from flask import request, redirect, jsonify, session, url_for

@app.route('/login')
def login():
    login_url = urls.get_login_url()
    return redirect(login_url)

@app.route('/connect/callback')
def callback():
    code = request.args.get('code')
    if not code:
        logger.error("缺少code参数")
        return jsonify(Response.error(400, "缺少code参数"))
    logger.info("【code】" + str(code))
    # 通过code请求到access_token
    token_url = urls.get_token_url(code)
    resp = Request.get(token_url, to_dict=False)
    print(resp)
    try:
        access_token = re.findall("access_token=(.*?)&expires_in", resp)[0]
        logger.info("【access_token】" + str(access_token))
    except IndexError:
        logger.error('获取access_token错误')
        return jsonify(Response.error(400, "获取access_token错误"))
    session['qq_access_token'] = access_token
    # 通过access_token得到openid
    openid_url = urls.get_openid_url(access_token)
    resp = Request.get(openid_url)
    print(resp)
    openid = resp.get('openid')
    logger.info("【openid】" + str(openid))
    session['openid'] = openid
    return redirect(url_for('get_user_info'))

@app.route('/userInfo')
def get_user_info():
    """
    从session中得到用户的access_token和openid得到用户的基本信息
    :return:
    """
    if 'qq_access_token' in session:
        openid = session.get('openid')
        access_token= session.get('qq_access_token')
        logger.info("【openid】" + str(openid))
        logger.info("【access_token】" + str(access_token))
        user_info_url = urls.get_user_info_url(access_token, openid)
        resp = Request.get(user_info_url)
        return jsonify(Response.success(resp))
    return jsonify(Response.error(400, "获取用户信息失败"))