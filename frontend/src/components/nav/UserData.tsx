import {Badge, Button, Overlay, OverlayTrigger, Tooltip} from "react-bootstrap";
import React, {useEffect, useRef, useState} from "react";
import {bottom} from "@popperjs/core";

/**
 * This component creates notifications popover also
 */
export function UserData() {
    const notificationsButtonRef: React.MutableRefObject<null | HTMLButtonElement> = useRef(null)
    const notificationsDivRef: React.MutableRefObject<null | HTMLDivElement> = useRef(null);
    const [hoverNotificationButton, setHoverNotificationButton] = useState(false)
    const [clickNotificationButton, setClickNotificationButton] = useState(false)

    const [notification, setNotification] = useState(1)

    const handleNotificationClick = () => {
        setHoverNotificationButton(false)
        setClickNotificationButton(val => !val)
    }

    const handleClickOutside = (e) => {
        if (notificationsDivRef.current && !notificationsDivRef.current.contains(e.target)) {
            setClickNotificationButton(false)
        }
    }

    const renderNotifications = () => {
        if (notification > 0) {
            return (
                <p className="text-danger fs-4 text-center h-100">
                    Brak powiadomień :(
                </p>
            )
        } else {
            return (
                <p>XD</p>
            )
        }
    }

    useEffect(() => {
        document.addEventListener('mousedown', handleClickOutside)
        return () => {
            document.removeEventListener('mousedown', handleClickOutside)
        }
    }, [])

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
                <div style={{width: 300, height: notification > 0 ? 500 : 44, position: "relative"}}
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
                <Button>
                    <img src="box-arrow-right.svg" alt="settings icon"/>
                </Button>
            </OverlayTrigger>
        </div>

    )
}