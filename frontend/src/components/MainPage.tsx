import {Outlet, useLocation, useNavigate} from "react-router";
import {NavBar} from "./nav/NavBar";
import {useDispatch, useSelector} from "react-redux";
import {isMessageEmptySelector, messageSelector, removeMessage} from "../features/error/ToastMessageSlice";
import {Toast, ToastContainer} from "react-bootstrap";
import {AppDispatch} from "../app/Store";
import {useEffect} from "react";

export function MainPage() {
    const toastEmpty = useSelector(isMessageEmptySelector)
    const toasts = useSelector(messageSelector)
    const navigate = useNavigate()
    const location = useLocation();

    const dispatch: AppDispatch = useDispatch()
    const handleToast = () => {
        if (!toastEmpty) {
            return (
                <ToastContainer position='bottom-end'>
                    {
                        Object.entries(toasts).map(([id, message]) => {
                            return (
                                <Toast animation={true} onClose={() => dispatch(removeMessage(id))} key={id}>
                                    <Toast.Header>{message.title}</Toast.Header>
                                    <Toast.Body>{message.description}</Toast.Body>
                                </Toast>
                            )
                        })
                    }
                </ToastContainer>
            )
        }
    }

    useEffect(() => {
        if (location.pathname === '/') {
            navigate('/offers')
        }
    }, [location, navigate])

    return (
        <div className="vh-100">
            <NavBar/>
            <Outlet/>
            {handleToast()}
        </div>
    )
}