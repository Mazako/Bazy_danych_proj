import {Badge, Button, Overlay, OverlayTrigger, Toast, Tooltip} from "react-bootstrap";
import React, {useEffect, useRef, useState} from "react";
import {bottom} from "@popperjs/core";
import {AppDispatch} from "../../app/Store";
import {useDispatch} from "react-redux";
import {logout} from "../../features/user/UserSlice";
import {useNavigate} from "react-router";
import {createMessage} from "../../features/error/ToastMessageSlice";
import {NotificationType} from "../../features/user/Notifications";
import {getNotificationsRequest, markAsSeenRequest} from "../../api/Requests";
import axios from "axios";

/**
 * This component creates notifications popover also
 */
export function UserData() {
    const notificationsButtonRef: React.MutableRefObject<null | HTMLButtonElement> = useRef(null)
    const notificationsDivRef: React.MutableRefObject<null | HTMLDivElement> = useRef(null);
    const [hoverNotificationButton, setHoverNotificationButton] = useState(false)
    const [clickNotificationButton, setClickNotificationButton] = useState(false)
    const [notifications, setNotifications] = useState([]);
    const [notification, setNotification] = useState(0)

    const dispatch: AppDispatch = useDispatch()
    const nav = useNavigate()
    const handleLogout = () => {
        dispatch(logout())
        nav('/offers')
        dispatch(createMessage({title: 'Wylogowano', description: 'Wylogowano pomyślnie'}))

    }
    const handleNotificationClick = () => {
        setHoverNotificationButton(false)
        setClickNotificationButton(val => !val)
    }

    const handleClickOutside = (e) => {
        if (notificationsDivRef.current && !notificationsDivRef.current.contains(e.target)) {
            setClickNotificationButton(false)
        }
    }

    useEffect(() => {
        const fetchNotifications = async () => {
            try {
                const response = await getNotificationsRequest();
                setNotifications(response.data.filter(n => !n.seen));
            } catch (error) {
                console.error("Error fetching notifications:", error);
            }
        };
        fetchNotifications();
        const intervalId = setInterval(fetchNotifications, 10000);
        return () => clearInterval(intervalId);
    }, []);

    useEffect(() => {
        document.addEventListener('mousedown', handleClickOutside)
        return () => {
            document.removeEventListener('mousedown', handleClickOutside)
        }
    }, [])

    const markAsSeen = async (notificationId) => {
        try {
            await markAsSeenRequest(notificationId);
            setNotifications(notifications.filter(n => n.id !== notificationId));
        } catch (error) {
            console.error("Error marking notification as seen:", error);
        }
    };
    const renderNotifications = () => {
        if (notifications.length === 0) {
            return (
                <p className="text-danger fs-4 text-center h-100 justify-content-center">
                    Brak powiadomień :(
                </p>
            );
        }

        return notifications.map(notification => {
            return (
                <Toast
                    key={notification.id}
                    className="mb-2"
                >
                    <Toast.Header closeButton onClick={() => markAsSeen(notification.id)}>
                        <i className={'me-2'}></i>
                        <strong className="me-auto">{notification.tourName}</strong>
                        <small>{notification.sendDate}</small>
                    </Toast.Header>
                    <Toast.Body>
                        {notification.tourName}
                        <p>{notification.departureDate} - {notification.returnDate}</p>
                    </Toast.Body>
                </Toast>
            );
        });
    };



    return (
        <div className="d-flex">

            <div>
                <Button ref={notificationsButtonRef}
                        onMouseOver={() => clickNotificationButton || setHoverNotificationButton(true)}
                        onMouseOut={() => setHoverNotificationButton(false)}
                        onClick={handleNotificationClick}
                        active={clickNotificationButton}>
                    <img src="bell-fill.svg" alt="notification icon"/>
                    <sup>
                        <Badge pill={true} className="position-absolute top-0 start-100 translate-middle bg-danger">
                            {notification}
                        </Badge>
                    </sup>
                </Button>
            </div>

            <Overlay show={hoverNotificationButton}
                     target={notificationsButtonRef}
                     placement={bottom}>
                <Tooltip style={{position: "fixed"}}>Powiadomienia</Tooltip>
            </Overlay>

            <Overlay target={notificationsButtonRef}
                     show={clickNotificationButton}
                     placement={'bottom-end'}
                     ref={notificationsDivRef}>
                <div style={{width: 300, height: notification > 0 ? 500 : "auto", position: "relative"}}
                     className="border border-black bg-light-subtle z-3 rounded-3">
                    {renderNotifications()}
                </div>
            </Overlay>

            <OverlayTrigger placement="bottom"
                            overlay={<Tooltip style={{position: "fixed"}}>Ustawienia konta</Tooltip>}>
                <Button className="mx-1">
                    <img src="gear-fill.svg" alt="settings icon"/>
                </Button>
            </OverlayTrigger>

            <OverlayTrigger placement="bottom" overlay={<Tooltip style={{position: "fixed"}}>Wyloguj się</Tooltip>}>
                <Button onClick={handleLogout}>
                    <img src="box-arrow-right.svg" alt="settings icon"/>
                </Button>
            </OverlayTrigger>
        </div>

    )
}