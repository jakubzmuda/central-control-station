import {useCallback} from 'react';
import {useNavigate} from 'react-router-dom';

const useAppNavigation = () => {
    const navigate = useNavigate();

    return useCallback(
        (path: string) => {
            navigate(`${path}`);
        }, [navigate]);
};

export default useAppNavigation;