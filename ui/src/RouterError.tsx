import React from 'react';
import { Link } from 'react-router-dom';

function RouterError(){
    return(
        <Link to = "/">
            <h1>
                Page not found. Please reroute to the homepage.
            </h1>
        </Link>
    );
}

export default RouterError;