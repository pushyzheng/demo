# encoding:utf-8

APPID = 'Your APPID'
SECRET = 'Your SECRET'

CALLBACK_URL = 'http://account.example.com/connect/callback'

QQ_LOGIN_URL = 'https://graph.qq.com/oauth2.0/show?which=Login&display=pc&response_type=code&client_id={appid}&redirect_uri={redirect_uri}&scope=get_user_info'
QQ_TOKEN_URL = 'https://graph.qq.com/oauth2.0/token?grant_type=authorization_code&client_id={appid}&client_secret={secret}&code={code}&redirect_uri={redirect_uri}'
QQ_OPENID_URL = 'https://graph.qq.com/oauth2.0/me?access_token={access_token}'
QQ_USER_INFO_URL = 'https://graph.qq.com/user/get_user_info?access_token={access_token}&oauth_consumer_key={oauth_consumer_key}&openid={openid}'

def get_token_url(code):
    return QQ_TOKEN_URL.format(
        appid = APPID,
        secret = SECRET,
        code = code,
        redirect_uri = CALLBACK_URL
    )

def get_openid_url(access_token):
    return QQ_OPENID_URL.format(
        access_token = access_token
    )

def get_user_info_url(access_token, openid):
    return QQ_USER_INFO_URL.format(
        access_token = access_token,
        oauth_consumer_key = APPID,
        openid = openid
    )

def get_login_url():
    return QQ_LOGIN_URL.format(
        appid = APPID,
        redirect_uri = CALLBACK_URL
    )


