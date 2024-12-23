import React, { useContext, useEffect, useState } from "react";
import styles from './errorBubble.module.css';
import { AppContext } from "../../context/context";

function ErrorBubble() {
    const context = useContext(AppContext);
    const [isVisible, setIsVisible] = useState(false);
    const [shouldRender, setShouldRender] = useState(false);

    useEffect(() => {
        if (context.errorMessage) {
            setShouldRender(true);
            setIsVisible(true);

            const timer = setTimeout(() => {
                setIsVisible(false);
                setTimeout(() => {
                    setShouldRender(false);
                }, 500);
            }, 3000);

            return () => clearTimeout(timer);
        }
    }, [context.errorMessage]);

    if (!shouldRender) {
        return null;
    }

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