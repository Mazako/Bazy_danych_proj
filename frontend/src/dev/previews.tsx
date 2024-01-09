import {ComponentPreview, Previews} from "@react-buddy/ide-toolbox";
import {ExampleLoaderComponent, PaletteTree} from "./palette";
import {MainPage} from "../components/MainPage";
import {NavBar} from "../components/nav/NavBar";
import {UserData} from "../components/nav/UserData";

const ComponentPreviews = () => {
    return (
        <Previews palette={<PaletteTree/>}>
            <ComponentPreview path="/PaletteTree">
                <PaletteTree/>
            </ComponentPreview>
            <ComponentPreview
                path="/ExampleLoaderComponent">
                <ExampleLoaderComponent/>
            </ComponentPreview>
            <ComponentPreview path="/Hello">
                <MainPage/>
            </ComponentPreview>
            <ComponentPreview path="/NavBar">
                <NavBar/>
            </ComponentPreview>
            <ComponentPreview path="/UserData">
                <UserData/>
            </ComponentPreview>
        </Previews>
    );
};

export default ComponentPreviews;