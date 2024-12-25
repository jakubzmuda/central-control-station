import React, {useContext} from "react";
import {AppContext} from "../../context/context";
import Switch from "../switch/switch";

function UserSwitch() {
    const context = useContext(AppContext);
    const currentUser = context.currentUser;
    const users = Array.from(new Set([currentUser, ...context.users].filter(user => !!user))).map(user => capitalize(user))

    if (!users.length) {
        return null;
    }

    return (
        <Switch value={capitalize(currentUser)} entries={users}
                onChange={(newUser: string) => context.setCurrentUser(newUser.toLowerCase())}/>
    );

    function capitalize(str: string) {
        return str.charAt(0).toUpperCase() + str.slice(1).toLowerCase();
    }
}

export default UserSwitch;
