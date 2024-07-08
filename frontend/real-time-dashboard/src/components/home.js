// src/components/Home.js
import React from 'react';
import { Table } from 'react-bootstrap';
import { AlarmFill, FileEarmarkText } from 'react-bootstrap-icons';
import Realtime from './realtime';
import Notifications from './notifications';
import Summary from './summary';

const Home = () => {
  return (
    <div className="container-fluid">
      <div className="row">
        <div className="col-md-6 p-3" style={{ maxHeight: '50vh', overflowY: 'scroll' }}>
          <Realtime />
        </div>
        <div className="col-md-6 p-3" style={{ maxHeight: '50vh', overflowY: 'scroll' }}>
          <Notifications />
        </div>
      </div>
      <div className="row">
        <div className="col-md-12 p-6 d-flex flex-column align-items-center justify-content-center">
          <Summary />
        </div>
      </div>
    </div>
  );
};

export default Home;
