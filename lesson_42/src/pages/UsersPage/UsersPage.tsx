import React, {useEffect, useState} from 'react';
import {User} from '../../model/User';
import UserService from '../../services/UserService';
import {Card, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow} from "@mui/material";


const UsersPage = () => {
    const [users, setUsers] = useState<Array<User>>([]);
    const [error, setError] = useState<string>('');


    useEffect(() => {
        UserService.getUsers()
            .then(response => setUsers(response))
            .catch(error => setError(error.message));
    }, []);

    return (
        <div>
            {error}
            <Card style={{width: 500}}>
            <TableContainer>
                <Table sx={{minWidth: 650}} aria-label="simple table">
                    <TableHead>
                        <TableRow>
                            <TableCell>User Name</TableCell>
                            <TableCell align="right">Password</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {users.map((user) => (
                            <TableRow
                                key={user.userName}
                                sx={{'&:last-child td, &:last-child th': {border: 0}}}
                            >
                                <TableCell component="th" scope="row">
                                    {user.userName}
                                </TableCell>
                                <TableCell align="right">
                                    {user.password}</TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
            </Card>
        </div>
    );
};

export default UsersPage;
