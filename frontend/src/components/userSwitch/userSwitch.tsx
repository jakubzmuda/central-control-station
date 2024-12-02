import React from "react";
import styles from './userSwitch.module.css';
import {MenuItem, Select} from "@mui/material";

function UserSwitch({users, selectedUser, onChange}: {
    users: string[],
    selectedUser: string,
    onChange: (selected: string) => void
}) {
    const color = '#E80F88';
    return (
        <div className={styles.container}>
            <Select
                value={selectedUser}
                label="Członek"
                onChange={(event) => {
                    onChange(event.target.value);
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
                    borderRadius: '8px',
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
}

export default UserSwitch;
