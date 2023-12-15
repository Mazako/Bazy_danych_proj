sql = 'INSERT INTO role_permission(role_id, permission_id) VALUES \n'

# for i in range(1, 61):
#     sql += '(2, {}), '.format(i)
#     if i % 4 == 0:
#         sql += '\n'
x = [    ('USERS_CREATE'), ('USERS_READ'), ('USERS_UPDATE'), ('USERS_DELETE'),
          ('CONTRACT_READ'), ('CONTRACT_MANAGE'),
          ('OPINION_CREATE'), ('OPINION_READ'), ('OPINION_UPDATE'), ('OPINION_DELETE'),
          ('NOTIFICATION_CREATE'), ('NOTIFICATION_READ'), ('NOTIFICATION_UPDATE'), ('NOTIFICATION_DELETE'),
          ('ROOM_CONTRACT_READ'), ('ROOM_CONTRACT_MANAGE'),
          ('ROOM_TOUR_READ'), ('ROOM_TOUR_MANAGE'),
          ('ROOM_READ'), ('ROOM_MANAGE'),
          ('FACILITY_READ'), ('FACILITY_MANAGE'),
          ('RESORT_READ'), ('RESORT_MANAGE'),
          ('ADDRESS_READ'), ('ADDRESS_MANAGE'),
          ('CITY_READ'), ('CITY_MANAGE'),
          ('TOUR_READ'), ('TOUR_MANAGE'),
          ('ROLE_READ'), ('ROLE_MANAGE'),
          ('PERMISSION_READ'), ('PERMISSION_MANAGE'),
          ('ROLE_PERMISSION_READ'), ('ROLE_PERMISSION_MANAGE')]
print(len(x))