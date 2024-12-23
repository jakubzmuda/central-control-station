import React, {useContext} from "react";
import styles from './userSwitch.module.css';
import {MenuItem, Select} from "@mui/material";
import AppStorage from "../../storage/appStorage";
import {AppContext} from "../../context/context";

function UserSwitch() {
    const storage = new AppStorage();
    const context = useContext(AppContext);
    const currentUser = storage.currentUser();
    const users = Array.from(new Set([currentUser, ...context.users].filter(user => !!user))).map(user => capitalize(user))
    const color = '#E80F88';

    if (!users.length) {
        return null;
    }

    return (
        <div className={styles.container}>
            <Select
                value={capitalize(currentUser)}
                label="Członek"
                onChange={(event) => {
                    storage.setCurrentUser(event.target.value.toLowerCase());
                }}
                MenuProps={{
                    PaperProps: {
                        sx: {
                            backgroundColor: '#323232FF',
                            color: color,
                        },
                    },
                }}
                sx={{
                    color: color,
                    fontSize: 16,
                    border: '1px solid ' + color,
                    borderRadius: '100px',
                    '& .MuiSelect-select': {
                        padding: '4px 16px',
                    },
                    '&.Mui-focused .MuiOutlinedInput-notchedOutline': {
                        borderColor: 'transparent',
                    },
                    '& .MuiSelect-icon': {
                        color: color,
                        fontSize: 32,
                        right: 4
                    },
                    '&.Mui-focused': {
                        outline: 'none',
                    },
                }}
            >
                {users.map(user => <MenuItem
                    key={user}
                    value={user}
                    sx={{
                        '& .MuiMenuItem-root.Mui-selected': {
                            backgroundColor: '#464646',
                        },
                        '& .MuiMenuItem-root.Mui-selected:hover': {
                            backgroundColor: '#464646',
                        },
                        '& .MuiMenuItem-root.Mui-selected:focus': {
                            backgroundColor: '#464646',
                        },
                        '&.Mui-selected': {
                            backgroundColor: '#464646',
                        },
                        '&.Mui-selected:hover': {
                            backgroundColor: '#464646',
                        },
                    }}
                >{user}</MenuItem>)}
            </Select>
        </div>
    );

    function capitalize(str: string) {
        return str.charAt(0).toUpperCase() + str.slice(1).toLowerCase();
    }
}

export default UserSwitch;
