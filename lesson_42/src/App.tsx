import React from 'react';
import { Route, Routes } from 'react-router-dom';
import UsersPage from './pages/UsersPage/UsersPage';

function App() {
    return (
        <Routes>
                 <Route path={'/users'} element={<UsersPage/>} />
        </Routes>
    );
}

export default App;