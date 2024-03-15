/*��Mock*/
const Mock = require('mockjs');
const Random = Mock.Random;

/*����һ���������ݵ�ͨ�ý��*/
let result = {
  msg: '',
  data: ''
}

/*ģ�����ݿ���Ϣ*/
let username = 'xiaolong';
let password = '123456';
let verityCode = 'abcde';

/**
 * ģ����֤��
 */
Mock.mock('/getVerifyCode', 'get', () => {
  result.data = {
    codeToken: Random.string(32),
    codeImg: Random.dataImage('75x40', verityCode)
  }
  return result;
})

/**
 * ���ص�½����
 */
Mock.mock('/login', 'post', (req) => {
  // ��ȡ��������
  let from = JSON.parse(req.body);
  //�ж���֤��
  if (verityCode === from.code) {
    // ��֤�˻�
    if (username === from.username) {
      // ��֤����
      if (password === from.password) {
        result.msg = '��½�ɹ�'
        result.data = 'true'
      } else {
        result.msg = '�������'
        result.data = 'false'
      }
    } else {
      result.msg = '�û�������'
      result.data = 'false'
    }
  } else {
    result.msg = '��֤�����'
    result.data = 'false'
  }
  return result;
})
