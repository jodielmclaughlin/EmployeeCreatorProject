import { HashRouter, Route, Routes, } from "react-router-dom";

import './App.css'
import EmployeeListPage from "./pages/EmployeeListPage";
import EditEmployee from "./pages/EditEmployee";
import CreateEmployee from "./pages/CreateEmployee";
import LoginPage from "./pages/LoginPage.tsx";
import Header from "./components/Navigation/Header.tsx";
import AdminRoute from "./components/Admin/AdminRoute.tsx";

function App() {
  return (
    <HashRouter>    
      <div>
        <Header />
        <Routes>
            {/*<Route path="/" element={<EmployeeListPage />} />*/}
            <Route path="/login" element={<LoginPage />} />
            <Route path="/employees/new" element={<AdminRoute><CreateEmployee /></AdminRoute>} />
            <Route path="/employees/:id/edit" element={<AdminRoute><EditEmployee /></AdminRoute>} />
            <Route path="/" element={<EmployeeListPage />} />
        </Routes>
        
      </div>
    </HashRouter>
  )
}


export default App
