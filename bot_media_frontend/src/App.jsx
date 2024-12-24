import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import Home from "./pages/home/home";
import Login from "./pages/login/login";

function App() {
  const isAuthenticated = localStorage.getItem("authToken");

  const publicRoutes = {
    "/login": <Login />,
  };

  const protectedRoutes = {
    "/": <Home />,
  };

  const ProtectedRoute = ({ children }) => {
    if (!isAuthenticated) {
      return <Navigate to="/login" />;
    }
    return children;
  };

  return (
    <BrowserRouter>
      <Routes>
        {Object.entries(publicRoutes).map(([path, element]) => (
          <Route key={path} path={path} element={element} />
        ))}

        {Object.entries(protectedRoutes).map(([path, element]) => (
          <Route
            key={path}
            path={path}
            element={<ProtectedRoute>{element}</ProtectedRoute>}
          />
        ))}
      </Routes>
    </BrowserRouter>
  );
}

export default App;
