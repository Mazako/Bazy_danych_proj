sql = 'INSERT INTO role_permission(role_id, permission_id) VALUES \n'

for i in range(1, 61):
    sql += '(2, {}), '.format(i)
    if i % 4 == 0:
        sql += '\n'
print(sql)