import React, {useContext, useEffect, useState} from "react";
import styles from './errorBubble.module.css';
import {AppContext} from "../../context/context";

function ErrorBubble() {
    const context = useContext(AppContext);
    const [isVisible, setIsVisible] = useState(false);

    useEffect(() => {
        if (context.errorMessage) {
            setIsVisible(true);

            const timer = setTimeout(() => {
                setIsVisible(false);
            }, 3000);

            return () => clearTimeout(timer);
        }
    }, [context.errorMessage]);

    return (
        <div
            className={styles.container}
            style={{
                opacity: isVisible ? 1 : 0,
                transition: 'opacity 0.5s ease',
            }}
        >
            {context.errorMessage}
        </div>
    );
}

export default ErrorBubble;
