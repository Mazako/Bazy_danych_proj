import {Badge, Button, OverlayTrigger, Tooltip} from "react-bootstrap";

export function UserData() {

    return (
        <div className="d-flex">

            <OverlayTrigger trigger="hover" placement="bottom"
                            overlay={<Tooltip style={{position: "fixed"}}>Powiadomienia</Tooltip>}>
                <div>
                    <Button>
                        <img src="bell-fill.svg" alt="notification icon"/>
                        <sup><Badge pill={true}
                                    className="position-absolute top-0 start-100 translate-middle bg-danger">1</Badge></sup>
                    </Button>
                </div>
            </OverlayTrigger>

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