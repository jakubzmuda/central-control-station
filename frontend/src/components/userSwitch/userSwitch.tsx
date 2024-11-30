import React from "react";
import styles from './userSwitch.module.css';
import {MenuItem, Select} from "@mui/material";

function UserSwitch({users, selectedUser, onChange}: {
    users: string[],
    selectedUser: string,
    onChange: (selected: string) => void
}) {
    return (
        <div className={styles.container}>
            <Select
                value={selectedUser}
                label="Członek"
                onChange={(event) => {
                    onChange(event.target.value);
                }}
                sx={{
                    color: "#fff",
                    '&.Mui-focused .MuiOutlinedInput-notchedOutline': {
                        borderColor: 'transparent', // Removes focus border color
                    },
                    '&.Mui-focused': {
                        outline: 'none', // Removes focus outline
                    },
                }}
            >
                {users.map(user => <MenuItem value={user}>{user}</MenuItem>)}
            </Select>
        </div>
    );
}

export default UserSwitch;
