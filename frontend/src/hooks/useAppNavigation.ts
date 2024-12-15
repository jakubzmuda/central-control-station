import {useCallback} from 'react';
import {useNavigate} from 'react-router-dom';

const useAppNavigation = () => {
    const navigate = useNavigate();

    return useCallback(
        (path: string) => {
            navigate(`/central-control-station${path}`);
        }, [navigate]);
};

export default useAppNavigation;